# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.6.0)
add_definitions( -ffunction-sections -fdata-sections -Ofast -ftree-vectorize -DNDEBUG )

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( jamesDSPImpulseToolbox
             SHARED
             JdspImpResToolbox.c
             AutoConvolver.c
             kissfft/kiss_fft.c
             kissfft/kiss_fftr.c)

target_link_libraries( jamesDSPImpulseToolbox
                       sndfile
                       samplerate )