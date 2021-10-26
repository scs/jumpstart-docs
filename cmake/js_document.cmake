cmake_minimum_required(VERSION 3.10)

find_package(docmake)
include(docmake/drawio)
include(docmake/gpp)
include(docmake/pandoc)
include(docmake/latex)
include(docmake/markdownlint)

include(helpers)


set(JS_CMAKE_DIR ${CMAKE_CURRENT_LIST_DIR})


function(js_script name sources)
    set(sources ${sources} ${ARGN})
    set(pdf_name ${name}.pdf)
    file(GLOB_RECURSE code "code/*")
    list(FILTER code EXCLUDE REGEX ".*/target/.*")
    list(FILTER code EXCLUDE REGEX ".*.idea/.*")

    get_subdirs(include_paths ${CMAKE_CURRENT_SOURCE_DIR})
    list(FILTER include_paths EXCLUDE REGEX ".*/target/.*")
    list(FILTER include_paths EXCLUDE REGEX ".*/code")
    list(APPEND include_paths ${code})

    gpp_preprocessor(
            SOURCES ${name}.md
            OUTPUT_LIST script_md
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/ ${include_paths}
    )

    setup_markdownlint(
            SOURCES ${sources}
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${pdf_name}
            SOURCES ${script_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    js_add_to_global_archive_file_list(${pdf_name})

    pandoc_resource_files(${pdf_name} ${CMAKE_CURRENT_SOURCE_DIR} ${code})

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${pdf_name} ${JS_CMAKE_DIR}/latex/ ${logos})

    js_add_images(${pdf_name})
    js_add_assets(${pdf_name})
endfunction()

function(js_exercise name sources)
    set(sources ${sources} ${ARGN})
    set(pdf_name ${name}.pdf)
    set(solution_pdf_name ${name}_solution.pdf)
    file(GLOB_RECURSE code "code/*")
    list(FILTER code EXCLUDE REGEX ".*/target/.*")
    list(FILTER code EXCLUDE REGEX ".*.idea/.*")

    get_subdirs(include_paths ${CMAKE_CURRENT_SOURCE_DIR})
    list(FILTER include_paths EXCLUDE REGEX ".*/target/.*")
    list(FILTER include_paths EXCLUDE REGEX ".*/code")
    list(APPEND include_paths ${code})

    gpp_preprocessor(
            SOURCES ${name}.md
            OUTPUT_LIST solution_md
            DEFINES solution
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/ ${include_paths}
    )

    gpp_preprocessor(
            SOURCES ${name}.md
            OUTPUT_LIST exercise_md
            DEFINES exercise
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/ ${include_paths}
    )

    setup_markdownlint(
            SOURCES ${sources}
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${solution_pdf_name}
            SOURCES ${solution_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    pandoc_document(
            TARGET ${pdf_name}
            SOURCES ${exercise_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    js_add_to_global_archive_file_list(${pdf_name} ${solution_pdf_name})

    pandoc_resource_files(${solution_pdf_name} ${CMAKE_CURRENT_SOURCE_DIR} ${code})
    pandoc_resource_files(${pdf_name} ${CMAKE_CURRENT_SOURCE_DIR} ${code})

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${solution_pdf_name} ${JS_CMAKE_DIR}/latex/ ${logos})
    pandoc_resource_files(${pdf_name} ${JS_CMAKE_DIR}/latex/ ${logos})

    js_add_images(${solution_pdf_name})
    js_add_images(${pdf_name})
    js_add_assets(${pdf_name})
endfunction()

function(js_slides name sources)
    set(sources ${sources} ${ARGN})
    set(pdf_name ${name}.pdf)
    file(GLOB_RECURSE code "code/*")
    list(FILTER code EXCLUDE REGEX ".*/target/.*")
    list(FILTER code EXCLUDE REGEX ".*.idea/.*")

    get_subdirs(include_paths ${CMAKE_CURRENT_SOURCE_DIR})
    list(FILTER include_paths EXCLUDE REGEX ".*/target/.*")
    list(FILTER include_paths EXCLUDE REGEX ".*/code")
    list(APPEND include_paths ${code})

    gpp_preprocessor(
            SOURCES ${name}.md
            OUTPUT_LIST slides_md
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/ ${include_paths}
    )

    setup_markdownlint(
            SOURCES ${sources}
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${pdf_name}
            SOURCES ${slides_md}
            HEADERS ${JS_CMAKE_DIR}/latex/slides_settings.tex
            PARAMS -t beamer -f markdown-implicit_figures -V lang=de-CH -V aspectratio=1610
            DEPENDS ${markdownlint_list}
    )

    js_add_to_global_archive_file_list(${pdf_name})

    pandoc_resource_files(${pdf_name} ${CMAKE_CURRENT_SOURCE_DIR} ${code})

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${pdf_name} ${JS_CMAKE_DIR}/latex/ ${logos})

    js_add_images(${pdf_name})
    js_add_assets(${pdf_name})
endfunction()


function(js_add_images project_target)
    file(GLOB_RECURSE drawio_xml "images/*.xml" "images/*.drawio")
    file(GLOB_RECURSE latex_tex "images/*.tex" "images/*.latex")
    file(GLOB_RECURSE normal_images "images/*.*")
    if(normal_images AND drawio_xml)
        list(REMOVE_ITEM normal_images ${drawio_xml})
    endif()
    if(normal_images AND latex_tex)
        list(REMOVE_ITEM normal_images ${latex_tex})
    endif()

    drawio_images(
            SOURCES ${drawio_xml}
            OUT_PATH drawio_binary_dir
            OUT_FILES drawio_images
    )

    latex_files(
            SOURCES ${latex_tex}
            OUT_PATH latex_binary_dir
            OUT_FILES latex_images
    )

    pandoc_resource_files(${project_target} ${drawio_binary_dir} ${drawio_images})
    pandoc_resource_files(${project_target} ${latex_binary_dir} ${latex_images})
    pandoc_resource_files(${project_target} ${CMAKE_CURRENT_SOURCE_DIR} ${normal_images})
endfunction()

function(js_add_assets project_target)
    file(GLOB_RECURSE assets "assets/*.*")

    pandoc_resource_files(${project_target} ${CMAKE_CURRENT_SOURCE_DIR} ${assets})
endfunction()

function(js_add_to_global_archive_file_list)
    get_property(local_list GLOBAL PROPERTY global_archive_file_list)
    foreach (arg ${ARGV})
        list(APPEND local_list ${CMAKE_CURRENT_BINARY_DIR}/${arg})
    endforeach ()
    set_property(GLOBAL PROPERTY global_archive_file_list ${local_list})
endfunction()

function(js_global_file_archive TARGET)
    get_property(global_archive_file_list GLOBAL PROPERTY global_archive_file_list)
    add_custom_command(
            OUTPUT ${TARGET}.zip
            COMMAND
            ${CMAKE_COMMAND} -E tar "cfv" "${TARGET}.zip" --format=zip ${global_archive_file_list}
            DEPENDS ${global_archive_file_list}
            COMMENT "Create archive: ${TARGET}.zip"
    )

    add_custom_target(
            ${TARGET}_archive
            DEPENDS ${TARGET}.zip
    )
endfunction()
