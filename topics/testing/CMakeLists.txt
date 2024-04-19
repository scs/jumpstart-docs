include(js_document)

js_slides(testing_slides testing_slides.md)
js_script(testing_exercise testing_exercise.md)
js_script(testing_additional_exercises testing_additional_exercises.md)

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*")
list(FILTER code EXCLUDE REGEX ".*/target/.*")
list(FILTER code EXCLUDE REGEX ".*iml")
list(FILTER code EXCLUDE REGEX ".*.idea/.*")
list(FILTER code EXCLUDE REGEX ".*data/.*")
js_add_to_global_archive_file_list(${code})
