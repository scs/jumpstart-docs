include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(build_systems_slides build_systems_slides.md ${slides_files})

js_exercise(build_systems_exercise build_systems_exercise.md)

if (JS_WITH_CODE)
    add_subdirectory(code)
endif ()
