include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(docker_slides docker_slides.md ${slides_files})
