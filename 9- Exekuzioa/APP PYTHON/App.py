import subprocess
import docker

import docker

def dockerIreki():
    # Docker irekitzeko komandoa
    docker_desktop_command = "Start-Process -NoNewWindow -FilePath 'C:\\Program Files\\Docker\\Docker\\Docker Desktop.exe'"

    try:
        # Try-catch bat docker irekitzen danean
        subprocess.run(["powershell", "-Command", docker_desktop_command], shell=True, check=True)
        print("Docker ondo ireki da.")
    except subprocess.CalledProcessError as e:
        print(f"Errore bat gertatu da Docker irekitzerakoan: {e}")

def klienteaSortu():
    client = docker.from_env()
    return client

def kontainerrakAteraEtaHasi():
    try:
        # Kliente sortu funtzioa deitu
        client = klienteaSortu()
        
        # Dockerreko kontainerren izena eta ID-a azaltzen du
        containers = client.containers.list()
        for container in containers:
            print(f"ID: {container.id}, Izena: {container.name}")
            # Kontainerraren status-a "Running" ez badago, martxan jartzen du, bestela ez
            if container.status != 'running':
                container.start()
                print(f"'{container.name}' Kontainerra martxan jarri da")
            else:
                print(f"'{container.name}' Kontainerra martxan dago")
                
    except Exception as e:
        print(f"Errore bat gertatu da kontainerrak martxan jartzerakoan: {e}")    

# Docker irekitzeko funtzioa deitu
dockerIreki()
# Kontainerrak ikusteko funtzioari deitu
kontainerrakAteraEtaHasi()

    