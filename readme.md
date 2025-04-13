# ☁️ Terraform EKS Jenkins Pipeline

This project automates the provisioning and destruction of an **Amazon EKS Cluster** using **Terraform**, controlled through a **Jenkins CI/CD pipeline**.

---

## 🚀 Project Overview

- **CI/CD Tool:** Jenkins (Declarative Pipeline)
- **Infrastructure-as-Code:** Terraform
- **Cloud Provider:** AWS (EKS)
- **Pipeline Stages:** Pull → Test → Deploy / Destroy
- **Use Case:** Spin up or destroy Kubernetes infrastructure on AWS with a single Jenkins job.

---

## 📁 Project Structure

```bash
terraform-eks/
│
├── main.tf              # EKS resources and provider config
├── variables.tf         # Terraform variables
│
Jenkinsfile              # Jenkins pipeline definition
```
---

# 🔄 Jenkins Pipeline Stages
1. 📥 Pull
Clones code from the GitHub repo.

2. 🧪 Test
Runs terraform init, terraform validate, and terraform plan.

3. 🚀 Deploy / 💣 Destroy
Runs terraform apply or terraform destroy based on branch or job.


## Prerequisites
- **AWS ACCOUNT:** credentials (access key & secret key)
- **EC2-INSTANCE:** t3.medium (image-ubuntu, 30gb volume)


# Configuration Steps

- Connect EC@2 instance via ssh on your local terminal
- Make sure port - 80 & 8080 is allowed
- Setup the Jenkins Server on it

## Configuration Commands
1. **Install java**
   ```sh
   sudo apt update
   sudo apt install fontconfig openjdk-17-jre -y
   java -version
   ```
2. **Install Jenkins Long Term Support**
   ```sh
   sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
   https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
   echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
   https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
   /etc/apt/sources.list.d/jenkins.list > /dev/null
   sudo apt-get update
   sudo apt-get install jenkins 
   sudo systemctl status jenkins
   ```
3. **Hit the public IP of an instance on the browser**
   ```sh
   <public-ip>:8080
   cat /var/lib/jenkins/secrets/initialAdminPassword
   copy token and paste it
   install suggested plugins
   give credentials and login
   ```   
   


