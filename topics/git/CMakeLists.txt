include(js_document)

file(GLOB_RECURSE files *.md)
js_slides(git_slides ${files})
