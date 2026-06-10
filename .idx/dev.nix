# To learn more about how to use Nix to configure your environment
# see: https://firebase.google.com/docs/studio/customize-workspace
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-24.05";

  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.jdk17 # ¡Fundamental! Gradle necesita Java para compilar el proyecto.
    pkgs.android-tools
    # Nota: IDX suele inyectar su propio Android SDK para el emulador. 
    # Si notas conflictos o lentitud, intenta quitar pkgs.android-sdk.
  ];

  # Sets environment variables in the workspace
  env = {
    # Puedes definir variables de entorno aquí si tu proyecto lo requiere
  };

  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      "fwcd.kotlin"
      "vscjava.vscode-java-pack"
    ];

    # Enable previews
    previews = {
      enable = true;
      previews = {
        android = {
          manager = "android";
          # El emulador necesita un comando para saber cómo compilar e instalar tu app.
          # Asegúrate de que ":app" coincida con el nombre de tu módulo principal.
          command = ["./gradlew" ":app:assembleDebug"];
        };
      };
    };

    # Workspace lifecycle hooks
    workspace = {
      # Runs when a workspace is first created
      onCreate = {
        # Puedes hacer que las dependencias se descarguen automáticamente la primera vez
        # gradle-install = "./gradlew build --dry-run";
      };
      # Runs when the workspace is (re)started
      onStart = {
      };
    };
  };
}