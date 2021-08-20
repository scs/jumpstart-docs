include(js_document)

file(GLOB_RECURSE files *.md)
js_slides(git_slides ${files})

js_add_to_global_archive_file_list(code/rebase_merge_example.sh)
