include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(docker_slides docker_slides.md ${slides_files})

file(GLOB_RECURSE code RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "code/*")
js_add_to_global_archive_file_list(${code})
