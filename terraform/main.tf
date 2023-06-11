terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region  = "ap-northeast-2"
}

resource "aws_instance" "multi-tenant" {
  ami           = "ami-027886247d2f15359"
  instance_type = "t2.micro"

  tags = {
    Name = "MultiTenantDemo"
  }
}
