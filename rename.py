import json, shutil

images = json.load(open("heroes.json"))
print(f"Found {len(images)} images to rename")
for i in images:
    print(f"Renaming {i[1]} to {i[0]}")
    shutil.copy(f"src/main/resources/static{i[1]}", f"iwdata/heroes/{i[0]}")
