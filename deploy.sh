#!/bin/bash

# Configuration
# Usage: ./deploy.sh <USER> <HOST> <KEY_PATH>
# Example: ./deploy.sh opc 123.45.67.89 ~/.ssh/id_rsa

USER=$1
HOST=$2
KEY_PATH=$3

if [ -z "$USER" ] || [ -z "$HOST" ] || [ -z "$KEY_PATH" ]; then
  echo "Usage: ./deploy.sh <USER> <HOST> <KEY_PATH>"
  echo "Example: ./deploy.sh opc 123.45.67.89 ~/.ssh/id_rsa"
  exit 1
fi

echo "Deploying to $USER@$HOST..."

# 1. Copy files to server
echo "Copying files..."
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no docker-compose.yml "$USER@$HOST:~/"
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no Dockerfile "$USER@$HOST:~/"
# Create directory for source if it doesn't exist
ssh -i "$KEY_PATH" -o StrictHostKeyChecking=no "$USER@$HOST" "mkdir -p ~/src"
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no -r src "$USER@$HOST:~/src"
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no build.gradle "$USER@$HOST:~/"
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no settings.gradle "$USER@$HOST:~/"
scp -i "$KEY_PATH" -o StrictHostKeyChecking=no -r gradle "$USER@$HOST:~/"

# 2. Setup and Run on Server
echo "Executing remote commands..."
ssh -i "$KEY_PATH" -o StrictHostKeyChecking=no "$USER@$HOST" << 'EOF'
  # Update and Install Docker if not present
  if ! command -v docker &> /dev/null; then
      echo "Installing Docker..."
      sudo dnf update -y
      sudo dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo
      sudo dnf install -y docker-ce docker-ce-cli containerd.io
      sudo systemctl start docker
      sudo systemctl enable docker
      sudo usermod -aG docker $USER
      echo "Docker installed. Please re-run the script to pick up group changes or handle manually."
      # We might need to exit here or use 'newgrp' but for automation simplicity we proceed
  fi

  # Install Docker Compose if not present
  if ! command -v docker-compose &> /dev/null; then
      echo "Installing Docker Compose..."
      sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
      sudo chmod +x /usr/local/bin/docker-compose
  fi

  # Check for .env file
  if [ ! -f .env ]; then
      echo "WARNING: .env file not found. Creating a default one. Please edit it!"
      touch .env
      echo "DB_PASSWORD=password" >> .env
      echo "DB_USERNAME=root" >> .env
  fi

  # Open Firewall ports (Oracle Linux specific, might fail on Ubuntu but that's ok)
  if command -v firewall-cmd &> /dev/null; then
      sudo firewall-cmd --permanent --add-port=8080/tcp
      sudo firewall-cmd --permanent --add-port=80/tcp
      sudo firewall-cmd --permanent --add-port=443/tcp
      sudo firewall-cmd --reload
  fi

  # Build and Run
  echo "Starting services..."
  # We use 'sudo' for docker commands in case the user group change didn't take effect immediately in this session
  sudo docker-compose down
  sudo docker-compose up -d --build

  echo "Deployment complete!"
EOF
