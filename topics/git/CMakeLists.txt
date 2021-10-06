include(js_document)

file(GLOB_RECURSE files *.md)
js_slides(git_slides ${files})

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*")
js_add_to_global_archive_file_list(${code})
