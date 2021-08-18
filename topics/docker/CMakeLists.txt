include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(docker_slides docker_slides.md ${slides_files})

js_add_to_global_archive_file_list(code/hostcontrold-solution/Dockerfile)
