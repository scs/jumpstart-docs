include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(build_systems_slides build_systems_slides.md ${slides_files})

js_script(build_systems_exercise build_systems_exercise.md)
js_script(bitbucket_pipelines_exercise bitbucket_pipelines_exercise.md)

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*")
list(FILTER code EXCLUDE REGEX "code/CMakeLists_solution.txt")
js_add_to_global_archive_file_list(${code})

if (JS_WITH_CODE)
    add_subdirectory(code)
endif ()
