set -e
src_base=`dirname $0`

cmake -S $src_base -B $src_base/build

make -C $src_base/build
make -C $src_base/build test
