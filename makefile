JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	asst3.java \
	MaxHeap.java \
	HeapElt.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class