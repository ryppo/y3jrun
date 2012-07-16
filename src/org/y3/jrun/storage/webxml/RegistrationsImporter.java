package org.y3.jrun.storage.webxml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.RichParticipation;

public class RegistrationsImporter {
	
	public static Discipline[] importDisciplines(URL registrationsFile) throws ParserConfigurationException, SAXException, IOException, ParseException {
		Discipline[] disciplines = null;
		ModelImporter modelImporter = new DisciplineImporter();
		disciplines = (Discipline[]) importModels(disciplines, modelImporter, registrationsFile, null);
		return disciplines;
	}
	
	public static RichParticipation[] importParticipations(URL registrationsFile, RichCompetition sourceCompetition) throws ParserConfigurationException, SAXException, IOException, ParseException {
		RichParticipation[] participations = null;
		ModelImporter modelImporter = new ParticipationImporter();
		participations = (RichParticipation[]) importModels(participations, modelImporter, registrationsFile, sourceCompetition);
		return participations;
	}
	
	public static Contact[] importContacts(URL registrationsFile) throws ParserConfigurationException, SAXException, IOException, ParseException {
		Contact[] contacts = null;
		ModelImporter modelImporter = new ContactImporter();
		contacts = (Contact[]) importModels(contacts, modelImporter, registrationsFile, null);
		return contacts;
	}
	
	private static Model[] importModels(Model[] models, ModelImporter modelImporter, URL registrationsFile, RichCompetition sourceCompetition) throws IOException, SAXException, ParserConfigurationException, ParseException {
		if (registrationsFile != null) {
			ArrayList<Model> importedModels = new ArrayList<Model>(0);
			InputStream in = registrationsFile.openStream();
			Document doc = parse(in);
			doc.getDocumentElement().normalize();
			Element element = doc.getDocumentElement();
			NodeList submissions = element.getChildNodes();
			int subCount = submissions.getLength();
			if (subCount != 0) {
				for (int subNo = 0; subNo < subCount; subNo++) {
					Node submission = submissions.item(subNo);
					if (submission.getNodeName().equals(
							KeywordsDictionary.REGISTRATIONS_XML_SUBMISSION)) {
						Model model = modelImporter.getNewModel();
						modelImporter.fillModel(submission, model, sourceCompetition);
						model.generateImportFingerPrint();
						System.out.println(model.getImportFingerPrint());
						importedModels.add(model);
					}
				}
			}
			models = importedModels.toArray(modelImporter.getNewModelArray(importedModels.size()));
		}
		return models;
	}
	
	private static Document parse (InputStream is) throws SAXException, IOException, ParserConfigurationException {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
        domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(false);
		domFactory.setNamespaceAware(false);
		builder = domFactory.newDocumentBuilder();
		ret = builder.parse(is);
        return ret;
    }
}
