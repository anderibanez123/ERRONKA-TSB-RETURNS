import docker
import subprocess


def dockerIreki():
    # Docker irekitzeko komandoa
    docker_desktop_command = "Start-Process -NoNewWindow -FilePath 'C:\\Program Files\\Docker\\Docker\\Docker Desktop.exe'"

    try:
        # Try-catch bat docker irekitzen danean
        subprocess.run(["powershell", "-Command", docker_desktop_command], shell=True, check=True)
        print("Docker ondo ireki da.")
    except subprocess.CalledProcessError as e:
        print(f"Errore bat gertatu da Docker irekitzerakoan: {e}")

def kontenedoreGuztiakPiztu():
    # Docker kliente bat sortu egiten du
    client = docker.from_env()

    # Kontainer lista bat lortzen du
    all_containers = client.containers.list(all=True)

    if not all_containers:
        print("Ez daude kontenedoreak.")
        return

    # Kontenedore bakoitza pizten du
    for container in all_containers:
        # Bere estatua konprobatzen du
        if container.status == 'exited':
            # Kontenedorea binkulatuta badago ikusten du
            links = container.attrs.get('HostConfig', {}).get('Links', [])

            # Berifikatu egiten du
            if links and any('/db' in link for link in links):
                # /db kontenedorea lehenengo pizten du, erroreak ez edukitzeko
                db_container = client.containers.get('db')
                if db_container.status == 'exited':
                    print("/db kontenedorea pizten arazoak ez edukitzeko besteekin.")
                    db_container.start()

            # Kontenedore aktuala pizten du
            print(f"Kontenedorea pizten: {container.name}")
            container.start()
        else:
            print(f" {container.name} kontenedorea piztuta dago.")

if __name__ == "__main__":
    dockerIreki()
    kontenedoreGuztiakPiztu()







