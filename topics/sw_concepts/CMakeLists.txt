include(js_document)

js_slides(sw_concept_slides sw_concept_slides.md)

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*")
js_add_to_global_archive_file_list(${code})
