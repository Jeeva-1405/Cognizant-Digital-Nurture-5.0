#!/bin/bash

git clone https://github.com/Jeeva-1405/framecape-website.git
cd framecape-website

git checkout -b feature/navbar-redesign

echo "Updated navbar markup" >> src/components/Navbar.jsx
git add src/components/Navbar.jsx
git status

git commit -m "Redesign navbar with responsive layout"

echo "Added mobile menu toggle" >> src/components/Navbar.jsx
git add src/components/Navbar.jsx
git commit -m "Add mobile menu toggle to navbar"

git log --oneline -n 5

git checkout main
git pull origin main

git checkout feature/navbar-redesign
git rebase main

git push -u origin feature/navbar-redesign

git checkout main
git merge feature/navbar-redesign
git push origin main

git branch -d feature/navbar-redesign
git push origin --delete feature/navbar-redesign

git branch -a
