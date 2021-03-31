cmake_minimum_required(VERSION 3.10)

find_package(docmake)
include(docmake/drawio)
include(docmake/gpp)
include(docmake/pandoc)
include(docmake/latex)
include(docmake/markdownlint)


set(JS_CMAKE_DIR ${CMAKE_CURRENT_LIST_DIR})


function(js_script file)
    file(GLOB_RECURSE code "code/*.*")

    gpp_preprocessor(
            SOURCES ${file}.md
            OUTPUT_LIST script_md
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/
    )

    setup_markdownlint(
            SOURCES ${file}.md
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${file}.pdf
            SOURCES ${script_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    pandoc_resource_files(${file}.pdf ${CMAKE_CURRENT_SOURCE_DIR} ${code})

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${file}.pdf ${JS_CMAKE_DIR}/latex/ ${logos})

    js_add_images(${file}.pdf)
endfunction()

function(js_exercise file)
    file(GLOB_RECURSE code "code/*.*")

    gpp_preprocessor(
            SOURCES ${file}.md
            OUTPUT_LIST solution_md
            DEFINES solution
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/
    )

    gpp_preprocessor(
            SOURCES ${file}.md
            OUTPUT_LIST exercise_md
            DEFINES exercise
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/
    )

    setup_markdownlint(
            SOURCES ${file}.md
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${file}_solution.pdf
            SOURCES ${solution_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    pandoc_document(
            TARGET ${file}.pdf
            SOURCES ${exercise_md}
            TEMPLATE ${JS_CMAKE_DIR}/latex/templates/eisvogel/eisvogel.tex
            HEADERS ${JS_CMAKE_DIR}/latex/script_settings.tex
            PARAMS --highlight-style tango --listings --number-sections -V colorlinks -V lang=de-CH -V listings-disable-line-numbers
            DEPENDS ${markdownlint_list}
    )

    pandoc_resource_files(${file}_solution.pdf ${CMAKE_CURRENT_SOURCE_DIR} ${code})
    pandoc_resource_files(${file}.pdf ${CMAKE_CURRENT_SOURCE_DIR} ${code})

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${file}_solution.pdf ${CMAKE_SOURCE_DIR}/latex/ ${logos})
    pandoc_resource_files(${file}.pdf ${CMAKE_SOURCE_DIR}/latex/ ${logos})

    js_add_images(${file}_solution.pdf)
    js_add_images(${file}.pdf)
endfunction()

function(js_slides file)
    gpp_preprocessor(
            SOURCES ${file}.md
            OUTPUT_LIST slides_md
            INCLUDE_PATHS ${JS_CMAKE_DIR}/latex/
    )

    setup_markdownlint(
            SOURCES ${file}.md
            OUTPUT_LIST markdownlint_list
            STYLE_FILE ${JS_CMAKE_DIR}/markdownlint.yml
    )

    pandoc_document(
            TARGET ${file}.pdf
            SOURCES ${slides_md}
            HEADERS ${JS_CMAKE_DIR}/latex/slides_settings.tex
            PARAMS -t beamer -f markdown-implicit_figures -V lang=de-CH -V aspectratio=1610
            DEPENDS ${markdownlint_list}
    )

    file(GLOB_RECURSE logos "${JS_CMAKE_DIR}/latex/logos/*.png")
    pandoc_resource_files(${file}.pdf ${JS_CMAKE_DIR}/latex/ ${logos})

    js_add_images(${file}.pdf)
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
