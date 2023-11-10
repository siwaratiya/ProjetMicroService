# build stage
FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /source
COPY . .

RUN dotnet restore "./App.WebApi/App.WebApi.csproj" --disable-parallel
RUN dotnet publish "./App.WebApi/App.WebApi.csproj" -c release -o /app

# Server Stage
FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS final
WORKDIR /app
COPY --from=build /app ./
EXPOSE 36516
ENTRYPOINT ["dotnet", "App.WebApi.dll"]
 




