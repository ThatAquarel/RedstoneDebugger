import colorsys
import numpy as np
from PIL import Image

overlay = np.array(Image.open("_overlay.png")) / 255
powered = np.array(Image.open("_powered.png")) / 255
background = np.array(Image.open("_background.png")) / 255

colors = 16
step = 360 / colors

for i in range(colors):
    hue = i * step
    rgb = np.array(colorsys.hls_to_rgb(hue / 360, 0.5, 1))

    overlay_ = overlay.copy()
    powered_ = powered.copy()

    overlay_[:, :, (0, 1, 2)] *= rgb
    powered_[:, :, (0, 1, 2)] *= rgb

    on = background.copy()
    off = background.copy()

    on[:, :, (0, 1, 2)] += overlay_[:, :, (0, 1, 2)] * overlay_[:, :, (3, 3, 3)] + \
                           powered_[:, :, (0, 1, 2)] * powered_[:, :, (3, 3, 3)]
    off[:, :, (0, 1, 2)] += overlay_[:, :, (0, 1, 2)] * overlay_[:, :, (3, 3, 3)]

    on *= 255
    off *= 255

    on, off = on.astype(np.uint8), off.astype(np.uint8)

    Image.fromarray(on).save(f"breakpoint_powered_{i}.png")
    Image.fromarray(off).save(f"breakpoint_{i}.png")
