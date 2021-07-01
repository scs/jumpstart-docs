include(js_document)

js_slides(testing_slides testing_slides.md)
js_exercise(testing_exercise testing_exercise.md)

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*.*")
list(FILTER code EXCLUDE REGEX ".*/target/.*")
list(FILTER code EXCLUDE REGEX ".*iml")
js_add_to_global_archive_file_list(${code})
