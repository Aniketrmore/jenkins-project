variable project {
    default = "cbz"
}
variable desired_nodes {
    default = 2
}
variable max_nodes {
    default = 2
}
variable min_nodes {
    default = 2
}
variable node_instance_type {
    default = "t3.micro"
}

variable "aws_access_key" {
  description = "AWS Access Key"
  type        = string
  sensitive   = true
}

variable "aws_secret_key" {
  description = "AWS Secret Key"
  type        = string
  sensitive   = true
}
