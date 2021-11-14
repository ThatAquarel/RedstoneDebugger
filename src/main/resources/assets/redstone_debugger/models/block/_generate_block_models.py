breakpoint_template = """{
  "parent": "block/cube_all",
  "textures": {
    "all": "debug:block/breakpoint_%i"
  }
}"""

breakpoint_powered_template = """{
  "parent": "block/cube_all",
  "textures": {
    "all": "debug:block/breakpoint_powered_%i"
  }
}"""

for i in range(16):
    on = open(f"breakpoint_{i}.json", "w+")
    off = open(f"breakpoint_powered_{i}.json", "w+")

    on.write(breakpoint_template % i)
    off.write(breakpoint_powered_template % i)

    on.close()
    off.close()
