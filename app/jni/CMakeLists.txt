# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.6.0)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you kbuild your app, Gradle
# automatically packages shared libraries with your APK.
add_subdirectory( ${CMAKE_CURRENT_SOURCE_DIR}/libsamplerate
                  ${lib_build_DIR} )

add_subdirectory( ${CMAKE_CURRENT_SOURCE_DIR}/libsndfile
                  ${lib_build_DIR} )

add_subdirectory( ${CMAKE_CURRENT_SOURCE_DIR}/main
                  ${lib_build_DIR} )
