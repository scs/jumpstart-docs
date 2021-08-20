#! /bin/bash -e

mkdir rebase_merge
cd rebase_merge
git init
echo "rebase_merge" > README.md
git add README.md
git commit -m "initial empty commit"

printf "my first line\n\ntells about the second line\n\nand the third will ignore both of them" > lines.txt
git add lines.txt
git commit -m "+ add lines file"

git checkout -b feature/numbers
printf "my 1. line\n\ntells about the 2. line\n\nand the 3. will ignore both of them" > lines.txt
git add lines.txt
git commit -m "* change words to numbers"
printf "The 1. line\n\ntells about the 2. line\n\nand the 3. will ignore both of them" > lines.txt
git add lines.txt
git commit -m "* change start of sentence"
printf "The 1. line\n\ntells about the 2. line\n\nand the 3. will be sad because of them" > lines.txt
git add lines.txt
git commit -m "* change behavior of third line"

git checkout master
printf "my first line\n\ntells about the second line\n\nand the third will ignore both of the others" > lines.txt
git add lines.txt
git commit -m "* change reference to the other lines"

git checkout feature/numbers
