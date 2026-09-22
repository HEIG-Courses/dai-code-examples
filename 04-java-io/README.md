# Java I/O: code examples

Examples for [chapter 04, Java I/O](https://heigvd-dai-26.github.io/chapters/04-java-io.html).
Try them in this order and read the comments in the code:

1. [Reading and writing binary data](01-reading-and-writing-binary-data):
   write the bytes 0 to 255 to a file, then read them back one at a time.
   Run the reader first to see what happens when the file does not exist.
2. [Reading and writing binary data with buffers](02-reading-and-writing-binary-data-with-buffers):
   the same with `BufferedInputStream` and `BufferedOutputStream`.
3. [Character encodings](03-character-encodings): "student" in five
   languages, encoded with five charsets and decoded as UTF-8.
4. [Reading and writing text data](04-reading-and-writing-text-data):
   copy a text file character by character with `FileReader` and
   `FileWriter`.
5. [Reading and writing text data with buffers](05-reading-and-writing-text-data-with-buffers):
   the same copy with `BufferedReader` and `BufferedWriter`.
6. [End of line characters](06-end-of-line-characters): copy line by line
   with the full `InputStreamReader` chain, writing `\n` after each line.
7. [Dealing with errors](07-dealing-with-errors): no `finally`, `finally`
   and `try`-with-resources on a missing file.
8. [Code points](08-code-points): `"I😂DAI"` with `charAt()` (6 `char`s)
   and with `codePoints()` (5 code points).
9. [Byte order mark](09-byte-order-mark): a CSV file that starts with a
   BOM, and how to remove it.
10. [Reading and writing blocks](10-reading-and-writing-blocks): copy a
    file in 64-byte blocks with `read(byte[])` and `write(buffer, 0, n)`.

The [`exercises`](exercises) directory holds the starting code of the
chapter's exercises.

## Usage

Go into the example's directory and run the file: `java File.java`
compiles and runs it in one step.

```sh
cd 01-reading-and-writing-binary-data
java BinaryWriteFileExample.java
java BinaryReadFileExample.java
```

Some examples read their own source file, so always run them from their
directory.
