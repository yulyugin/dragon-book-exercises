
# Copyright (c) 2019 Evgenii Iuliugin
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

option(BUILD_IMAGES "Build TeX images" OFF)

if(BUILD_IMAGES)
    find_package(LATEX COMPONENTS PDFLATEX)
    find_program(PDF2SVG pdf2svg REQUIRED)
    set(IMG_DIR ${CMAKE_BINARY_DIR}/img)

    if(NOT LATEX_FOUND)
        message(FATAL_ERROR "TeX not found.")
    endif()

    function(build_tex_image TEX_FILE)
        set(TEX_OPTS "-interaction=nonstopmode")
        execute_process(
            COMMAND ${PDFLATEX_COMPILER} ${TEX_OPTS} ${TEX_FILE} -output-directory=${IMG_DIR}
            COMMAND_ERROR_IS_FATAL ANY
        )
        get_filename_component(TEX_NAME ${TEX_FILE} NAME_WLE)
        string(REGEX REPLACE "[.]tex$" ".svg" SVG_FILE ${TEX_FILE})
        execute_process(
            COMMAND ${PDF2SVG} "${IMG_DIR}/${TEX_NAME}.pdf" "${SVG_FILE}"
            COMMAND_ERROR_IS_FATAL ANY
        )
    endfunction()
else()
    function(build_tex_image TEX_FILE)
        message(STATUS "Skipping image generation for ${TEX_FILE}")
    endfunction()
endif()
