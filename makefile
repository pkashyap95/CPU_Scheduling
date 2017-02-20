rebuild: clean compile

clean:
	@rm -rf Scheduler.class *~
	@rm -rf Process.class *~
	@rm -rf reader.class *~

compile: Scheduler.java
	@javac Scheduler.java


test: rebuild
	@java Scheduler csv.txt


