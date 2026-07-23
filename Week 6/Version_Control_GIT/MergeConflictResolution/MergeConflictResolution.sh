#!/bin/bash

git checkout main
git checkout -b feature/pricing-update

cat > src/components/Pricing.jsx << 'EOF'
function Pricing() {
  return <div>Starter Plan: Rs.4999/month</div>;
}
export default Pricing;
EOF

git add src/components/Pricing.jsx
git commit -m "Update starter plan pricing to 4999"

git checkout main
git checkout -b hotfix/pricing-currency

cat > src/components/Pricing.jsx << 'EOF'
function Pricing() {
  return <div>Starter Plan: $59/month</div>;
}
export default Pricing;
EOF

git add src/components/Pricing.jsx
git commit -m "Switch pricing display to USD"

git checkout main
git merge feature/pricing-update

git merge hotfix/pricing-currency

git status

cat src/components/Pricing.jsx

cat > src/components/Pricing.jsx << 'EOF'
function Pricing() {
  return <div>Starter Plan: Rs.4999/month ($59 USD)</div>;
}
export default Pricing;
EOF

git add src/components/Pricing.jsx
git commit -m "Resolve pricing conflict - show both currencies"

git log --graph --oneline --all

git branch -d feature/pricing-update hotfix/pricing-currency
