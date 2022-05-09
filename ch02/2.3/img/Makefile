PNG:=$(patsubst %.tex,%.png,$(wildcard *.tex))

all: $(PNG)

%.pdf: %.tex
	pdflatex $<

%.png: %.pdf
	pdftoppm -png $< -r 300 > $@
