include(js_document)

js_slides(build_systems_slides)
js_exercise(build_systems_exercise)

if (JS_WITH_CODE)
    add_subdirectory(code)
endif ()
