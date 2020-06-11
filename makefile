CC=javac
JAR=jar
EXE=java
PROJECT_NAME=COS314_A3_u18171185
TAR_FILE=${PROJECT_NAME}.tar
TAR_DIR=..
TAR_INPUT=makefile ReadMe.txt Report.pdf *.java bin
MAIN=Main
BIN=./bin
JAR_FILE=${PROJECT_NAME}.jar
MANIFEST_FILE=Manifest.txt
MANIFEST_CONTENT="Main-Class: ${MAIN}"
INPUT_FILE=params.txt iris.data iris.data
OUTPUT_FILE=*_output.txt Manifest.txt
m=Automated commit with MakeFile

make: *.java
	$(CC) *.java

jar: make
	echo ${MANIFEST_CONTENT} > ${MANIFEST_FILE}
	$(JAR) cmvf $(MANIFEST_FILE) $(JAR_FILE) *.class 
	mkdir -p ${BIN}
	mv $(JAR_FILE) ${BIN} 
	cp ${INPUT_FILE} ${BIN}

run: 
	$(EXE) $(MAIN) $(INPUT_FILE) $(BASIC_FILE)

exec:
	${EXE} -${JAR} ${BIN}/${JAR_FILE} $(INPUT_FILE)

test: run
	make basic

clean:
	-rm -f *.class $(OUTPUT_FILE)

wipe: clean
	-rm -f -r ${BIN} ${BuildDir}

rs:
	-rm -f $(OUTPUT_FILE) ${BIN}/${JAR_FILE}

again: make
	clear
	make run

tar:
	tar -cvz ${TAR_INPUT} -f ${TAR_DIR}/${TAR_FILE}

git:
	git add ./
	git commit -m "$(m)"
	git push

status:
	git status;