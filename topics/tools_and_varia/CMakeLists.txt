include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(tools_and_varia_slides tools_and_varia_slides.md ${slides_files})
