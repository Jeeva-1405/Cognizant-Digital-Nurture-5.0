#!/bin/bash

git clone https://github.com/Jeeva-1405/framecape-website.git
cd framecape-website

git remote add upstream https://github.com/cognizant-org/framecape-website.git
git remote -v

git fetch upstream
git checkout main
git merge upstream/main

git checkout -b fix/blog-listing-pagination

echo "Added pagination controls to blog listing" >> src/pages/Blog.jsx
git add src/pages/Blog.jsx
git commit -m "Add pagination controls to blog listing page"

echo "Fixed page size calculation" >> src/pages/Blog.jsx
git add src/pages/Blog.jsx
git commit -m "Fix page size calculation in blog pagination"

git push origin fix/blog-listing-pagination

git fetch upstream
git checkout main
git merge upstream/main
git checkout fix/blog-listing-pagination
git rebase main

git push origin fix/blog-listing-pagination --force-with-lease

git log --oneline upstream/main..fix/blog-listing-pagination

git checkout main
git branch -d fix/blog-listing-pagination
