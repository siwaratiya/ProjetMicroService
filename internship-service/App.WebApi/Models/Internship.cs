using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using App.WebApi.Settings;
using internship_service.Models;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using Newtonsoft.Json.Converters;

namespace App.ApplicationCore.Domain
{
    [Serializable , BsonIgnoreExtraElements]//decorating 
    public class Internship
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("username")]
        public string Username { get; set; } = String.Empty;

        [BsonElement("nom")]
        public string Nom { get; set; } = String.Empty;

        [BsonElement("prenom")]
        public string Prenom { get; set; } = String.Empty;

        [BsonElement("subject")]
        public string Subject { get; set; } = String.Empty;

        [BsonElement("university")]
        public string University { get; set; } = String.Empty;

        [BsonElement("phone_university")]
        [RegularExpression(@"^\d{8}$", ErrorMessage = "Phone University must be 8 digits.")]
        public int PhoneUniversity { get; set; }

        [BsonElement("datecreation")]
        public DateTime DateCreation { get; set; }

        [BsonElement("status")]
        [JsonConverter(typeof(StatusConverter))] // Use Newtonsoft.Json to convert string to enum
        public Status Status { get; set; }

         [BsonElement("studylevel")]
       [JsonConverter(typeof(StudyLevelConverter))] // Use Newtonsoft.Json to convert string to enum
        public Studylevel Studylevel { get; set; }
    }
}
