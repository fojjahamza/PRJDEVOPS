resource "null_resource" "docker-compose-up" {
count = var.action == "up" ? 1 : 0
provisioner "local-exec" {
    command = "docker-compose up"
}
}

resource "null_resource" "docker-compose-down" {
count = var.action == "down" ? 1 : 0
provisioner "local-exec" {
    command = "docker-compose down"
}
}
