using internship_service.Models;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace App.WebApi.Settings
{
    public class StudyLevelConverter : JsonConverter<Studylevel>
    {
        public override Studylevel Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            if (reader.TokenType == JsonTokenType.String)
            {
                string statusStr = reader.GetString();
                if (Enum.TryParse(statusStr, ignoreCase: true, out Studylevel status))
                {
                    return status;
                }
            }
            else if (reader.TryGetInt32(out int intValue))
            {
                return (Studylevel)intValue;
            }
            return Studylevel.LICENCE; // Provide a default value or handle the case when conversion fails.
        }

        public override void Write(Utf8JsonWriter writer, Studylevel value, JsonSerializerOptions options)
        {
            writer.WriteStringValue(value.ToString());// writer.WriteNumberValue((int)value);
        }
    }
}
