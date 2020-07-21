#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

#define N_CHARS 10

static int
reload_buffer(char *buffer, int fd, bool first_half)
{
        char *start = first_half ? buffer : (buffer + N_CHARS + 1);
        ssize_t ret = read(fd, start, N_CHARS);

        if (ret < 0) {
                perror("read");
                return -1;
        }

        if (ret != N_CHARS)
                start[ret] = 0;

        start[N_CHARS] = 0;
        return ret;
}

/* Read the next character from the input buffer.
   Zero indicated end of file. */
char
nextchar(char *buffer, int fd, char **forward)
{
        char ret = **forward;

        (*forward)++;
        if (**forward == 0) {
                if (*forward == buffer + N_CHARS) {
                        reload_buffer(buffer, fd, false);
                        (*forward)++;
                } else if (*forward == buffer + 2 * N_CHARS + 1) {
                        reload_buffer(buffer, fd, true);
                        (*forward) = buffer;
                } else {
                        return 0;
                }
        }

        return ret;
}

int
main(int argc, char *argv[])
{
        if (argc != 2) {
                printf("Not enough arguments.\nUsage: %s <file name>\n",
                       argv[0]);
                exit(0);
        }

        int fd = open(argv[1], 0);
        if (fd < 0) {
                perror("open");
                exit(1);
        }

        /* N valid characters + sentinel */
        char *buffer = malloc(2 * (N_CHARS + 1));
        int ret = reload_buffer(buffer, fd, true);
        if (ret <= 0) {
                printf("Empty input file\n");
                exit(0);
        }

        char c;
        char *forward = buffer;
        while ((c = nextchar(buffer, fd, &forward)) > 0) {
                printf("%c", c);
        }

        if (close(fd) < 0) {
                perror("close");
                exit(1);
        }
        exit(0);
}
