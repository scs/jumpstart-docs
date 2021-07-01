include(js_document)

file(GLOB_RECURSE slides_files slides/*.md)
js_slides(practice_in_programming_slides practice_in_programming_slides.md ${slides_files})
