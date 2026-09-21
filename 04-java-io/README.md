# Java I/O: code examples

Examples for [chapter 04, Java I/O](https://heigvd-dai-26.github.io/chapters/04-java-io.html).
Try them in this order and read the comments in the code:

1. [Reading and writing binary data](01-reading-and-writing-binary-data)
2. [Reading and writing binary data with buffers](02-reading-and-writing-binary-data-with-buffers)
3. [Character encodings](03-character-encodings)
4. [Reading and writing text data](04-reading-and-writing-text-data)
5. [Reading and writing text data with buffers](05-reading-and-writing-text-data-with-buffers)
6. [End of line characters](06-end-of-line-characters)
7. [Dealing with errors](07-dealing-with-errors)
8. [Code points](08-code-points)
9. [Byte order mark](09-byte-order-mark)

The [`exercises`](exercises) directory holds the starting code of the
chapter's exercises.

## Usage

Go into the example's directory and run the file directly (Java 11 or
later compiles it on the fly):

```sh
cd 01-reading-and-writing-binary-data
java BinaryWriteFileExample.java
java BinaryReadFileExample.java
```

Some examples read their own source file, so always run them from their
directory.
