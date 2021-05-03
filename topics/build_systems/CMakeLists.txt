include(js_document)

js_slides(build_systems_slides build_systems_slides.md)
js_exercise(build_systems_exercise build_systems_exercise.md)

if (JS_WITH_CODE)
    add_subdirectory(code)
endif ()
