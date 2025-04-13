variable "aws_access_key" {}
variable "aws_secret_key" {}
variable "aws_region" {
  default = "ap-southeast-2"
}
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


