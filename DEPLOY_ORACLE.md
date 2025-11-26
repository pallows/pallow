# Deploying Pallow to Oracle Cloud Infrastructure (OCI)

This guide details how to deploy the Pallow application to an Oracle Cloud Compute Instance using Docker and Docker Compose.

## Prerequisites

1.  **Oracle Cloud Account**: You need an active OCI account.
2.  **SSH Key Pair**: Generate an SSH key pair for accessing the instance.
3.  **Domain (Optional)**: If you want to access the app via a domain name.

## Step 1: Create a Compute Instance

1.  Log in to the OCI Console.
2.  Navigate to **Compute** -> **Instances**.
3.  Click **Create Instance**.
4.  **Name**: `pallow-server` (or your preference).
5.  **Image and Shape**:
    *   **Image**: Oracle Linux 8 or 9 (Canonical Ubuntu is also fine).
    *   **Shape**: VM.Standard.E2.1.Micro (Always Free) or Ampere A1 (Always Free, ARM-based).
        *   *Note*: If using Ampere (ARM), ensure the Docker images support `linux/arm64`. The provided Dockerfile builds on the host architecture. If building on x86 and deploying to ARM, you need multi-arch builds. **Recommendation: Use AMD64 (E2.1.Micro) or build specifically for ARM.**
6.  **Networking**: Create a new VCN or use an existing one. Ensure it has a public subnet.
7.  **SSH Keys**: Upload your public key file (`.pub`).
8.  Click **Create**.

## Step 2: Configure Security List (Firewall)

1.  In the Instance details page, click on the **Subnet** link.
2.  Click on the **Security List** for the subnet.
3.  Add **Ingress Rules**:
    *   **Source CIDR**: `0.0.0.0/0`
    *   **IP Protocol**: TCP
    *   **Destination Port Range**: `80`, `443`, `8080` (for app), `22` (SSH).
4.  *Note*: You also need to open ports in the instance's internal firewall (iptables/firewalld).

## Step 3: Connect to the Instance

```bash
ssh -i /path/to/private_key opc@<INSTANCE_PUBLIC_IP>
```
*(Username is usually `opc` for Oracle Linux or `ubuntu` for Ubuntu)*

## Step 4: Install Dependencies

Run the following commands on the server:

```bash
# Update system
sudo dnf update -y

# Install Docker
sudo dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo
sudo dnf install -y docker-ce docker-ce-cli containerd.io
sudo systemctl start docker
sudo systemctl enable docker

# Add user to docker group
sudo usermod -aG docker $USER

# Install Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Install Git
sudo dnf install -y git

# Open Firewall ports (Oracle Linux)
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --permanent --add-port=80/tcp
sudo firewall-cmd --permanent --add-port=443/tcp
sudo firewall-cmd --reload
```

*Log out and log back in for group changes to take effect.*

## Step 5: Deploy Application

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/pallow/pallow.git
    cd pallow
    ```

2.  **Configure Environment**:
    Create a `.env` file with your secrets:
    ```bash
    nano .env
    ```
    Paste the following (fill in your values):
    ```env
    DB_PASSWORD=secure_db_password
    DB_USERNAME=root
    JWT_SECRET_KEY=your_very_long_secret_key
    MAIL_USERNAME=your_email@gmail.com
    MAIL_PASSWORD=your_app_password
    CLIENT_ID=kakao_client_id
    REDIRECT_URI=http://<INSTANCE_IP>:8080/login/oauth2/code/kakao
    KAKAO_MAP_APP_KEY=kakao_map_key
    AWS_ACCESS_KEY=aws_key
    AWS_SECRET_KEY=aws_secret
    FLASK_URL=http://flask-server-ip:5000
    ```

3.  **Start Services**:
    ```bash
    docker-compose up -d --build
    ```

4.  **View Logs**:
    ```bash
    docker-compose logs -f
    ```

## Step 6: Access the Application

Open your browser and navigate to:
`http://<INSTANCE_PUBLIC_IP>:8080`

## Troubleshooting

-   **Database Connection**: Ensure the `db` service is healthy.
-   **Memory Issues**: If the build fails on a micro instance (1GB RAM), consider building the image locally and pushing to a registry (Docker Hub, OCR), then pulling it on the server.
    -   *Local*: `docker build -t myuser/pallow:latest . && docker push myuser/pallow:latest`
    -   *Server*: Update `docker-compose.yml` to use `image: myuser/pallow:latest` instead of `build: .`.

## Automated Deployment

I have included a `deploy.sh` script to automate the file transfer and setup.

**Usage:**
1.  Open Git Bash or your terminal.
2.  Run the script:
    ```bash
    ./deploy.sh <USER> <HOST> <KEY_PATH>
    ```
    Example:
    ```bash
    ./deploy.sh opc 123.45.67.89 ~/.ssh/id_rsa
    ```

This script will:
-   Copy all necessary project files to the server.
-   Install Docker and Docker Compose (if missing).
-   Configure the firewall.
-   Start the application.

