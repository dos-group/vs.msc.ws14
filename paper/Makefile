texFile=llncs

all: clean pdf

clean:
	rm -rf target

target:
	mkdir target

pdf: target
	pdflatex -output-directory target $(texFile).tex
	bibtex target/$(texFile).aux
	pdflatex -output-directory target $(texFile).tex
	pdflatex -output-directory target $(texFile).tex

