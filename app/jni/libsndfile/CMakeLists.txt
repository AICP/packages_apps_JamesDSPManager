# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.6.0)
set( -ffunction-sections -fdata-sections -Ofast -ftree-vectorize -DNDEBUG )

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( sndfile
             STATIC
             aiff.c
             alac.c
             alaw.c
             audio_detect.c
             au.c
             avr.c
             broadcast.c
             caf.c
             cart.c
             chanmap.c
             chunk.c
             command.c
             common.c
             dither.c
             double64.c
             dwd.c
             dwvw.c
             file_io.c
             flac.c
             float32.c
             g72x.c
             gsm610.c
             htk.c
             id3.c
             ima_adpcm.c
             ima_oki_adpcm.c
             ircam.c
             mat4.c
             mat5.c
             mpc2k.c
             ms_adpcm.c
             nist.c
             nms_adpcm.c
             ogg.c
             ogg_opus.c
             ogg_vorbis.c
             paf.c
             pcm.c
             pvf.c
             raw.c
             rf64.c
             rx2.c
             sd2.c
             sds.c
             sndfile.c
             strings.c
             svx.c
             txw.c
             ulaw.c
             voc.c
             vox_adpcm
             w64.c
             wav.c
             wavlike.c
             wve.c
             xi.c
             ALAC/ag_dec.c
             ALAC/ag_enc.c
             ALAC/alac_decoder.c
             ALAC/alac_encoder.c
             ALAC/ALACBitUtilities.c
             ALAC/dp_dec.c
             ALAC/dp_enc.c
             ALAC/matrix_dec.c
             ALAC/matrix_enc.c
             G72x/g72x.c
             G72x/g721.c
             G72x/g723_16.c
             G72x/g723_24.c
             G72x/g723_40.c
             GSM610/add.c
             GSM610/code.c
             GSM610/decode.c
             GSM610/gsm_create.c
             GSM610/gsm_decode.c
             GSM610/gsm_destroy.c
             GSM610/gsm_encode.c
             GSM610/gsm_option.c
             GSM610/long_term.c
             GSM610/lpc.c
             GSM610/preprocess.c
             GSM610/rpe.c
             GSM610/short_term.c
             GSM610/table.c )

include_directories( . )