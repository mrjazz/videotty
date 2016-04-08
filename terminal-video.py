from subprocess import Popen, PIPE
from time import time


CHARACTERS = [' ', '.', ',', ':', ';', 'i', '1', 't', 'f', 'L', 'C', 'G', '0', '8', '@']

HEIGHT = 240
WIDTH = 426

DISPLAY_MODES = {
  'RESET_ALL': '0',
  'BOLD': '1',
  'DIM': '2',
  'UNDERLINED': '4',
  'BLINK': '5',
  'REVERSE': '7',
  'HIDDEN': '8',
  'RESET_BOLD': '21',
  'RESET_DIM': '22',
  'RESET_UNDERLINED': '24',
  'RESET_BLINK': '25',
  'RESET_REVERSE': '27',
  'RESET_HIDDEN': '28'
}

def imageToAscii(data):
    contrastFactor = 2.95;
    line = 0
    for y in range(0, HEIGHT, 6):
        ascii = ''
        for x in range(0, WIDTH, 3):
            offset = (y * WIDTH + x) * 3
            r = max(0, min(contrastFactor * (ord(data[offset]) - 128) + 128, 255))
            g = max(0, min(contrastFactor * (ord(data[offset + 1]) - 128) + 128, 255))
            b = max(0, min(contrastFactor * (ord(data[offset + 2]) - 128) + 128, 255))
            brightness = 1 - (0.299 * r + 0.587 * g + 0.114 * b) / 255
            ascii += CHARACTERS[int(round(brightness * 14))]
        #ascii += "\n"
        echo(ascii, 0, line)
        line += 1
    #return ascii

def encodeToVT100(code):
    return u'\u001b' + str(code);

def echo(char, x, y):
    print(
        encodeToVT100("[" + str(y + 1) + ";" + str(x + 1) + "f") +
        str(char) +
        encodeToVT100("[" + DISPLAY_MODES['RESET_ALL'] + "m")
    )

sec = time()
frames = 0

command = ["ffmpeg", "-i", "the_matrix.mp4", "-f", "image2pipe", "-pix_fmt", "rgb24", "-vcodec", "rawvideo", "-loglevel", "quiet", "-nostats", "-"]

p = Popen(command, stdout = PIPE)
while True:
    data = p.stdout.read(WIDTH * HEIGHT * 3)
    if len(data) == 0:
        break

    imageToAscii(data)

    if (time() != sec):
        echo('__' + str( frames / (time() - sec) ) + '__', 145, 1)
        sec = time()
        frames = 0
    frames += 1

p.wait()