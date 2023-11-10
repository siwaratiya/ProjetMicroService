using internship_service.Models;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace App.WebApi.Settings
{
    public class StatusConverter : JsonConverter<Status>
    {
        public override Status Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            if (reader.TokenType == JsonTokenType.String)
            {
                string statusStr = reader.GetString();
                if (Enum.TryParse(statusStr, ignoreCase: true, out Status status))
                {
                    return status;
                }
            }
            else if (reader.TryGetInt32(out int intValue))
            {
                return (Status)intValue;
            }
            return Status.ONHOLD; // Provide a default value or handle the case when conversion fails.
        }
        public override void Write(Utf8JsonWriter writer, Status value, JsonSerializerOptions options)
        {
            writer.WriteStringValue(value.ToString());  //writer.WriteNumberValue((int)value);
        }
    }
}
