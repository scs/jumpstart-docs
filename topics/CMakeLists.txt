include(js_document)

add_subdirectory(admin)
add_subdirectory(build_systems)
add_subdirectory(docker)
add_subdirectory(git)
add_subdirectory(linux)
add_subdirectory(testing)
add_subdirectory(tools_and_varia)

js_global_file_archive(jumpstart)
