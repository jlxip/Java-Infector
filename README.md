<h1>JAVA INFECTOR</h1>

<h2>Summary (TL;DR)</h2>
<hr>
<p>Java Infector is a program made in multi-platform java.
With it you can insert a java class into any jar file, and "bind" both files.</p>
<br><br>
<h2>How does it work?</h2>
<hr>
<p>You have to select the file to infect, then, the program will read the template (template.java)
in the same directory. Java Infector will decompress the jar to infect.
Then it will change the Main-Class of the manifest to yours,
and it will change "/* --- TEMPLATE --- */" (without quotes) to a code which executes the old
main class (with reflection).
It will compile the template and move it to the output jar for finally compress all together.</p>
<br><br>
<h2>How can I use it?</h2>
<hr>
<p>Quite simple, create a new file called "template.java", and code (like if it was a jar executable,
using "public static void main(String[] args)". At the end of the function, when you like the program to continue,
insert the next line:<br>
/* --- TEMPLATE --- */
<br>And put the file (always in .java, do not compile it) in the root of the program. That's all, run the program, 
select the jar to infect and wait a few seconds.</p>