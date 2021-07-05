include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(build_systems_slides build_systems_slides.md ${slides_files})

js_exercise(build_systems_exercise build_systems_exercise.md)

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR}
        code/*.c*
        code/*.h*
        )
js_add_to_global_archive_file_list(code/CMakeLists_exercise.txt)
js_add_to_global_archive_file_list(${code})

if (JS_WITH_CODE)
    add_subdirectory(code)
endif ()
