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
4. **🔐 Jenkins Credentials Setup (Inject credentials)**   
   - Go to Jenkins → Manage Jenkins → Credentials

   - Add:

     - aws-access-key-id → Secret Text

     - aws-secret-access-key → Secret Text

5. **Install Terraform Manually on Jenkins server**
   - Go to Jenkins Server → Terminal

   - Commands to install terraform

   ```sh
   sudo apt-get update && sudo apt-get install -y gnupg software-properties-common
   ```
   ```sh
   wget -O- https://apt.releases.hashicorp.com/gpg | \
   gpg --dearmor | \
   sudo tee /usr/share/keyrings/hashicorp-archive-keyring.gpg > /dev/null
   ```
   ```sh
   gpg --no-default-keyring \
   --keyring /usr/share/keyrings/hashicorp-archive-keyring.gpg \
   --fingerprint
   ```
   ```sh
   echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] \
   https://apt.releases.hashicorp.com $(lsb_release -cs) main" | \
   sudo tee /etc/apt/sources.list.d/hashicorp.list
   ```
   ```sh
   sudo apt update
   ```
   ```sh
   sudo apt-get install terraform
   ```
   ```sh
   terraform --version
   ```
6. **Install AWS CLI**
   ```sh
   curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
   ```
   ```sh
   sudo apt-get install unzip -y
   ```
   ```sh
   unzip awscliv2.zip
   ```
   ```sh
   sudo ./aws/install
   ``` 
   ```sh
   aws --version
   ```
7. **Install kubectl binary with curl on Linux Terminal**
   ```sh
   curl -LO https://dl.k8s.io/release/v1.32.0/bin/linux/amd64/kubectl
   ```
   ```sh
   chmod +x kubectl
   ```
   ```sh
   sudo mv kubectl /usr/local/bin/
   ```
   ```sh
   kubectl version --client
   ```

8. **Create Pipeline**
   - Go to Jenkins → New Item → Select Pipeline → ok → give description
   - Now Add the Pipeline script that is given in the following github 
     repository
   ```sh
   https://github.com/Aniketrmore/jenkins-project.git
   move to terraform-eks directory
   open the jenkins-terraform-apply.groovy file
   copy the code 
   paste it in the jenkins pipeline script
   ```
   - Click on save

9. **Build Now**
   - Click on build now
   - wait 5-10 min
   - check console logs
   - The Build is successfull

## ✅ The EKS-CLUSTER has been deployed

   - To check cluster's nodes
   - To do work on the eks-cluster

10. **Access the eks-cluster**

   - Go to aws console → Select region ap-southeast-2 → Open the CloudShell terminal on it → Run the following command
   
   ```sh
   aws eks --region ap-southeast-2 update-kubeconfig --name cbz-cluster
   kubectl get nodes
   ```
   ```sh 
                *********************************
   ```

## 💡 Tips
- Use terraform init and terraform validate before any plan/apply steps.

- (Optional) Use plugins of AWS CLI, Kubectl (for post-deploy verification)

- Use kubectl or aws-cli only if installed and configured in Jenkins 
  agent.

- No extra indentation between cat <<EOF and the YAML → keeps YAML at 
  correct top-level format.

- Make sure to ignore sensitive files using .gitignore.

11. **🧼 Clean Up**

- Go to Jenkins → Move to the Cluster Creation Pipeline that previously 
  created → configure → remove all the previous pipeline 
  code → 
- Now Add the Pipeline script that is given in the following github 
     repository
   ```sh
   https://github.com/Aniketrmore/jenkins-project.git
   move to terraform-eks directory
   open the jenkins-terraform-destroy.groovy file
   copy the code 
   paste it in the jenkins pipeline script
   ```
   - Click on save

12. **Build Now**
   - Click on build now
   - wait 5-10 min
   - check console logs
   - The Build is successfull
   - The Infrastructure has been destroyed

   ```sh 
                *********************************
   ```

# ⚙️ Jenkins CI/CD Pipeline Flow
## Visualizing the Jenkins pipeline with Pull, Test, and Deploy/Destroy stages.

```
+------------------+
|  GitHub Repo     |
| (Terraform Code) |
+--------+---------+
         |
         | Webhook or Poll SCM
         v
+------------------+
|  Jenkins Pipeline|
+------------------+
         |
         v
+-------------------+
| Stage: Pull       |
| - Clone GitHub    |
+-------------------+
         |
         v
+-------------------+
| Stage: Test       |
| - terraform init  |
| - terraform plan  |
| - terraform validate |
+-------------------+
         |
         v
+-----------------------------+
| Stage: Deploy or Destroy    |
| - terraform apply or destroy|
| - auto-approve              |
+-----------------------------+
```
# 🔐 Secure Credential Handling (Jenkins + Terraform)
## Shows how credentials are securely injected.

```
+-------------------------+
| Jenkins Credentials     |
| (Secret Text)           |
| - aws-access-key-id     |
| - aws-secret-access-key |
+-------------------------+
         |
         v
+----------------------------+
| Jenkinsfile Environment    |
| environment {              |
|   AWS_ACCESS_KEY_ID        |
|   AWS_SECRET_ACCESS_KEY    |
| }                          |
+----------------------------+
         |
         v
+--------------------------+
| Terraform Variables      |
| - aws_access_key         |
| - aws_secret_key         |
+--------------------------+
```
# 🏗️ EKS Infrastructure Architecture Diagram
## This shows what Terraform will provision on AWS.
```
+-------------------------------------------------------+
|                  AWS Cloud (ap-southeast-2)           |
|                                                       |
|  +----------------+     +------------------------+    |
|  | EKS Cluster    |<--->|  Managed Node Group     |    |
|  | (cbz-cluster)  |     |  (EC2 worker nodes)     |    |
|  +----------------+     +------------------------+    |
|          |                         |                  |
|          |      +------------------+--------------+   |
|          |      | IAM Roles, VPC, Subnets, SGs    |   |
|          +----->| Created using Terraform AWS EKS |   |
|                 +----------------------------------+   |
+-------------------------------------------------------+
```
# 📄 License
## MIT — free to use, modify, and distribute.
      

   



